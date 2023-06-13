package kr.ac.pool.guestbook.controller.repository.service;

import kr.ac.pool.guestbook.dto.GuestBookDTO;
import kr.ac.pool.guestbook.dto.PageRequestDTO;
import kr.ac.pool.guestbook.dto.PageResultDTO;
import kr.ac.pool.guestbook.entity.GuestBook;
import kr.ac.pool.guestbook.service.GuestBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GusetBookServiceTests {
    @Autowired
    private GuestBookService service;

    @Test
    public void testRegister(){
        GuestBookDTO guestBookDTO = GuestBookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("userSample")
                .build();

        System.out.println(service.register(guestBookDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResultDTO<GuestBookDTO, GuestBook> resultDTO = service.getList(pageRequestDTO);
        System.out.println("prev: " + resultDTO.isPrev());
        System.out.println("next: " + resultDTO.isNext());
        System.out.println("total: " + resultDTO.getTotalPage());
        System.out.println("===================================");
        for (GuestBookDTO guestBookDTO : resultDTO.getDtoList()){
            System.out.println(guestBookDTO);
        }
        resultDTO.getDtoList().forEach(i -> System.out.println(i));
    }
}
