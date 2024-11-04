package com.example.inssurify.domain;

import com.example.inssurify.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bank extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}