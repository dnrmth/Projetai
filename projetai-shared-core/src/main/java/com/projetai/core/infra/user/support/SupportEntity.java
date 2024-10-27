package com.projetai.core.infra.user.support;

import com.projetai.core.domain.user.support.Support;
import com.projetai.core.infra.user.UserEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "support")
public class SupportEntity extends UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_available")
    private boolean isAvailable;

    public SupportEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public boolean getIsAvailable() {
        return this.isAvailable;
    }

    public SupportEntity(Support support) {
        super(support.getName(), support.getEmail());
        this.isAvailable = support.isAvailable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        if (!super.equals(o)) return false;

        SupportEntity that = (SupportEntity) o;
        return isAvailable == that.isAvailable && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, isAvailable);
    }
}