package com.fourward.linkchart.controller;

import com.fourward.linkchart.dto.MailDTO;
import com.fourward.linkchart.service.IMailService;
import com.fourward.linkchart.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MailController {
    private final IMailService mailService;

    @GetMapping(value = "mail/mailForm")
    public String mailForm() throws Exception {
        return "/mail/mailForm";
    }

    @PostMapping(value = "mail/sendMail")
    public String sendMail(HttpServletRequest request, ModelMap model) throws Exception {

        // 로그 찍기(추후 찍은 로그를 통해 이 함수에 접근했는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + "mail.sendMail start!");

        // 웹 URL로부터 전달받는 값들
        String toMail = CmmUtil.nvl(request.getParameter("toMail")); // 받는사람
        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
        String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

        // 메일 발송할 정보 넣기 위한 DTO객체 생성하기
        MailDTO pDTO = new MailDTO();

        // 웹에서 받은 값을 DTO에 넣기
        pDTO.setToMail(toMail); // 받는 사람을 DTO 저장
        pDTO.setTitle(title); // 제목을 DTO 저장
        pDTO.setContents(contents); // 내용을 DTO 저장

        //메일방송하기
        int res = mailService.doSendMail(pDTO);

        if (res == 1) { //메일발송 성공
            log.info(this.getClass().getName() + "mail.sendMail success!!!");

        } else { //메일발송 실패
            log.info(this.getClass().getName() + "mail.sendMail fail!!!");

        }

        //메일 발송 결과를 JSP에 전달하기(데이터 전달시, 숫자보단 문자열이 컨트롤하기 편리하기 때문에 강제로 숫자를 문자로 변환함)
        model.addAttribute("res", String.valueOf(res));

        // 로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + "mail.sendMail end!");

        // 함수 처리가 끝나고 보여줄 JSP 파일명(/WEB-INF/view//mail/sendMailResult.jsp)
        return "/mail/sendMailResult";
    }
}