package com.kenny.sdeappbackend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequestDTO {
    private String taskName;
    private int numberOfWorkingDays;
    private List<String> staffNames;
    private List<String> locations;

}
