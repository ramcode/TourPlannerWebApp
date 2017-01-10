package com.ridetour.backend.domains;

import com.google.common.base.MoreObjects;
import org.springframework.data.domain.Persistable;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by eyal on 5/22/2016.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class PersistableEntity<PK extends Serializable> implements Serializable, Persistable<PK> {

    private static final long serialVersionUID = 5554304839188669754L;
    PK id;

    @Id
    @Override
    @Nullable
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Access(AccessType.PROPERTY)
    public PK getId() {
        return id;
    }

    protected void setId(final PK id) {
        this.id = id;
    }

    @Override
    public abstract String toString();

    MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(this)
                .add("id", MoreObjects.firstNonNull(getId(), "none"));
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        PersistableEntity<?> that = (PersistableEntity<?>) obj;
        return null != this.getId() && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }
}
