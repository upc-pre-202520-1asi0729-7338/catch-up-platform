package com.acme.catchup.platform.news.domain.model.aggregates;

import com.acme.catchup.platform.news.domain.model.commands.CreateFavoriteSourceCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class FavoriteSource extends AbstractAggregateRoot<FavoriteSource> {
    @Id
    private Long id;

    @Column(nullable = false)
    @Getter
    private String newsApiKey;

    @Column(nullable = false)
    @Getter
    private String sourceId;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    protected FavoriteSource() {
    }

    public FavoriteSource(CreateFavoriteSourceCommand command) {
        this.newsApiKey = command.newsApiKey();
        this.sourceId = command.sourceId();
    }


}
