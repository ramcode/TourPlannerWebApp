package com.ridetour.backend.services.amazon;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.ridetour.backend.services.TConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by eyal on 5/27/2016.
 */
@Service
@Scope("prototype")
public class AmazonServices implements TConstants {
    private final Logger logger;

    {
        logger = LoggerFactory.getLogger(AmazonServices.class);
    }

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private PutObjectResult upload(String filePath, String uploadKey) throws FileNotFoundException {
        return upload(new FileInputStream(filePath), uploadKey);
    }

    private PutObjectResult upload(InputStream inputStream, String uploadKey) {
        return upload(inputStream, uploadKey, new ObjectMetadata());
    }

    private PutObjectResult upload(InputStream inputStream, String uploadKey, ObjectMetadata metadata) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, metadata);

        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

        IOUtils.closeQuietly(inputStream);

        return putObjectResult;
    }

    public List<PutObjectResult> upload(MultipartFile[] multipartFiles) {
        List<PutObjectResult> putObjectResults = new ArrayList<>();

        Arrays.stream(multipartFiles)
                .filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
                .forEach(multipartFile -> putObjectResults.add(upload(multipartFile)));

        return putObjectResults;
    }

    public PutObjectResult upload(MultipartFile multipartFile) {
        PutObjectResult putObjectResult = null;
        String originalFileName = multipartFile.getOriginalFilename();
        if (!StringUtils.isEmpty(originalFileName)) {
            String key = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFileName, ".");
            try {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.addUserMetadata(FILE_META_DATA_ORIGINAL, originalFileName);
                metadata.addUserMetadata(FILE_META_DATA_KEY, key);
                putObjectResult = upload(multipartFile.getInputStream(), key, metadata);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return putObjectResult;
    }

    public ResponseEntity<byte[]> download(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);

        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(objectInputStream);

        String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public List<S3ObjectSummary> list() {
        ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));

        return objectListing.getObjectSummaries();
    }

    public boolean file_delete_byVersion(String keyName) {
        try {
            // Make the bucket version-enabled.
            enableVersioningOnBucket(bucket);

            // Add a sample object.
            String versionId = putAnObject(keyName);

            amazonS3Client.deleteVersion(
                    new DeleteVersionRequest(
                            bucket,
                            keyName,
                            versionId));
            return true;
        } catch (AmazonServiceException ase) {
            logger.error("Caught an AmazonServiceException.");
            logger.error("Error Message:    " + ase.getMessage());
        } catch (AmazonClientException ace) {
            logger.error("Caught an AmazonClientException.");
            logger.error("Error Message: " + ace.getMessage());
        }
        return false;
    }

    private void enableVersioningOnBucket(String bucketName) {
        BucketVersioningConfiguration config = new BucketVersioningConfiguration()
                .withStatus(BucketVersioningConfiguration.ENABLED);
        SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(
                bucketName, config);
        amazonS3Client.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
    }

    private String putAnObject(String keyName) {
        PutObjectRequest request = new PutObjectRequest(bucket, keyName, "")
                .withCannedAcl(CannedAccessControlList.AuthenticatedRead);
        PutObjectResult response = amazonS3Client.putObject(request);
        return response.getVersionId();
    }

    public boolean file_delete(String keyName) {
        try {
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, keyName));
            return true;
        } catch (AmazonClientException ace) {
            logger.error("Caught an AmazonClientException.");
            logger.error("Error Message: " + ace.getMessage());
        }
        return false;
    }

    public String getUrl(String resource) {
        return "http://" + bucket + ".s3.amazonaws.com/" + resource;
    }
}
