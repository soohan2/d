package kr.ac.pool.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuestBookDTO {
    private Long gno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate, modDate;

}
