package com.runningmate.server.domain.calendar.model;

import com.runningmate.server.domain.politicians.model.Election;
import com.runningmate.server.domain.user.model.User;
import com.runningmate.server.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.ZoneId;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE user SET status='N' where id = ?")
@SQLRestriction("status = 'Y'")
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleType type;

    @Column(nullable = true)
    private Long electionId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Schedule from(Election election) {
        return Schedule.builder()
                .date(election.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .name(election.getName())
                .type(ScheduleType.ELECTION)
                .electionId(election.getId())
                .build();
    }
}

