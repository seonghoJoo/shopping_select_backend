package naver.shopping.select.controller;

import naver.shopping.select.model.Folder;
import naver.shopping.select.model.UserRoleEnum;
import naver.shopping.select.security.UserDetailsImpl;
import naver.shopping.select.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final FolderService folderService;

    @Autowired
    public HomeController(FolderService folderService) {
        this.folderService = folderService;
    }
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("nickname", userDetails.getUser().getNickname());
        if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN) {
            model.addAttribute("admin_role", true);
        }
        List<Folder> folderList = folderService.getFolders(userDetails.getUser());
        model.addAttribute("folders", folderList);

        return "index";
    }
}