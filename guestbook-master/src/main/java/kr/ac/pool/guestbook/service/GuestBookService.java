package kr.ac.pool.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import kr.ac.pool.guestbook.dto.GuestBookDTO;
import kr.ac.pool.guestbook.dto.PageRequestDTO;
import kr.ac.pool.guestbook.dto.PageResultDTO;
import kr.ac.pool.guestbook.entity.GuestBook;

public interface GuestBookService {
    Long register(GuestBookDTO dto);
    PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO);
    GuestBookDTO read(Long gno);

    void modify(GuestBookDTO dto);
    void remove(Long gno);
    BooleanBuilder getSearch(PageRequestDTO requestDTO);

    default GuestBook dtoToEntity(GuestBookDTO dto){
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestBookDTO entityToDto(GuestBook entity){

        GuestBookDTO dto = GuestBookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}
