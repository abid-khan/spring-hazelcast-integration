package com.hazelcast.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.jpa.domain.AbstractPersistable;

@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractEntity extends AbstractPersistable<Long> {

    @Version
    protected Long version;

}
