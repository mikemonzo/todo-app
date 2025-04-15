package com.example.todo.task.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.proxy.HibernateProxy;
import com.example.todo.category.model.Category;
import com.example.todo.tag.model.Tag;
import com.example.todo.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Lob
    private String description;
    private boolean completed;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_task_category"))
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "task_tag", joinColumns = @JoinColumn(name = "task_id"),
            foreignKey = @ForeignKey(name = "fk_task_tag_task"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            inverseForeignKey = @ForeignKey(name = "fk_task_tag_tag"))
    @Builder.Default
    @Setter(AccessLevel.NONE)
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_task_user"))
    private User author;

    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass)
            return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                        .hashCode()
                : getClass().hashCode();
    }
}
