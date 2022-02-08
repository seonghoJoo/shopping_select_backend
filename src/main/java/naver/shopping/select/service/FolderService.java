package naver.shopping.select.service;

import naver.shopping.select.model.Folder;
import naver.shopping.select.model.Product;
import naver.shopping.select.model.User;
import naver.shopping.select.repository.FolderRepository;
import naver.shopping.select.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, ProductRepository productRepository) {
        this.folderRepository = folderRepository;
        this.productRepository = productRepository;
    }


    // 로그인한 회원에 폴더들 등록
    @Transactional
    public List<Folder> addFolders(List<String> folderNames, User user) {

        List<Folder> folderList = new ArrayList<>();

        if(!checkRemoveFolderNameDuplicated(folderNames,user)){
            throw new IllegalStateException("중복된 폴더명이 존재합니다!");
        }

        for (String folderName : folderNames) {
            Folder folder = new Folder(folderName, user);
            folderList.add(folder);
        }

        return folderRepository.saveAll(folderList);
    }

    public boolean checkRemoveFolderNameDuplicated(List<String> folderNames, User user){

        Set<String> folderSet = new HashSet<>();
        // 입력 중복 체크
        for(String folderName : folderNames){
            folderSet.add(folderName);
        }
        if(folderSet.size() != folderNames.size()) return false;

        // DB 중복 체크
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        for(Folder folder : existFolderList){
            for(String folderName : folderNames){
                if(folder.getName().equals(folderName)){
                    return false;
                }
            }
        }
        return true;
    }

    // 로그인한 회원이 등록된 모든 폴더 조회
    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    public Page<Product> getProductInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc, User user) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page,size,sort);

        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }
}