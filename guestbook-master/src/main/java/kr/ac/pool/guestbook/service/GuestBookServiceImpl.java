package kr.ac.pool.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.pool.guestbook.dto.GuestBookDTO;
import kr.ac.pool.guestbook.dto.PageRequestDTO;
import kr.ac.pool.guestbook.dto.PageResultDTO;
import kr.ac.pool.guestbook.entity.GuestBook;
import kr.ac.pool.guestbook.entity.QGuestBook;
import kr.ac.pool.guestbook.repository.GuestBookRepositoty;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService{
    private final GuestBookRepositoty repositoty;
    @Override
    public Long register(GuestBookDTO dto) {
        GuestBook entity = dtoToEntity(dto);
        log.info(entity);
        repositoty.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        PageRequest pageable = requestDTO.getPageable(Sort.by("gno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<GuestBook> result = repositoty.findAll(pageable);
        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        Optional<GuestBook> result = repositoty.findById(gno);
        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void modify(GuestBookDTO dto) {
        Optional<GuestBook> result = repositoty.findById(dto.getGno());
        if(result.isPresent()){
            GuestBook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repositoty.save(entity);
        }
    }

    @Override
    public void remove(Long gno) {
        repositoty.deleteById(gno);
    }

    // 검색 조회 기능은 QueryDSL을 사용한다.
    @Override
    public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;
        BooleanExpression expression = qGuestBook.gno.gt(0L);

        booleanBuilder.and(expression);

        // 검색형식이 선택되어 있지 않은 경우
        if (type == null || type.trim().length()==0){
            return booleanBuilder;
        }

        // 검색조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")){
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }
        if (type.contains("c")){
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }
        if (type.contains("w")){
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
