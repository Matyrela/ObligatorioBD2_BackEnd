package me.basedatos2.pencaucu.persistance.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EnrollId implements Serializable {
    private static final long serialVersionUID = 8851479488079680673L;
    @Column(name = "career_id", nullable = false)
    private Integer careerId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EnrollId entity = (EnrollId) o;
        return Objects.equals(this.studentId, entity.studentId) &&
                Objects.equals(this.careerId, entity.careerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, careerId);
    }

}