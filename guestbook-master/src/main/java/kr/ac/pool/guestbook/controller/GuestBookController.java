package kr.ac.pool.guestbook.controller;

import kr.ac.pool.guestbook.dto.GuestBookDTO;
import kr.ac.pool.guestbook.dto.PageRequestDTO;
import kr.ac.pool.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestBookController {
    private final GuestBookService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("페이지 요청 정보" + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }
    // 등록 화면을 보여줌(Get방식)
    @GetMapping("/register")
    public void register(){
        log.info("show register");
    }

    // 등록 처리 후에 목록페이지를 이동
    @PostMapping("/register")
    public String registerPost(GuestBookDTO dto, RedirectAttributes redirectAttributes){
        log.info("등록처이 후 목록페이지 리다이렉트");
        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("gno: " + gno);
        GuestBookDTO dto = service.read(gno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        log.info("gno: " + gno);
        service.remove(gno);
        redirectAttributes.addFlashAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestBookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("gno", dto.getGno());
        return "redirect:/guestbook/read";
    }
}
