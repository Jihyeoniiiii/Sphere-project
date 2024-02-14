package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.ProjectPlatform;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.domain.mapping.ProjectTechStack;

import com.sphere.demo.web.dto.ProjectRequestDto.UpdateDto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import java.util.Set;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer view;

    @Enumerated(EnumType.STRING)
    private ProjectState status;

    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 작성자

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)

    private Set<ProjectPlatform> projectPlatformSet;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectRecruitPosition> projectRecruitPositionSet;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<ProjectTechStack> projectTechStackSet;


    public void setUser(User user) {
        if (this.user != null) {
            throw new IllegalStateException();
        }
        this.user = user;
        user.getProjectList().add(this);
    }

    public void viewUp() {
        this.view++;
    }

    public void update(UpdateDto updateDto) {
        this.title = updateDto.getTitle();
        this.body = updateDto.getBody();
        this.startDate = updateDto.getStartDate();
        this.endDate = updateDto.getEndDate();
        this.deadline = updateDto.getDeadline();
    }

}
