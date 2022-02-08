package naver.shopping.select.service;

import naver.shopping.select.dto.request.ProductMypriceRequestDto;
import naver.shopping.select.dto.request.ProductRequestDto;
import naver.shopping.select.dto.response.ProductPagingResponseDto;
import naver.shopping.select.model.Folder;
import naver.shopping.select.model.Product;
import naver.shopping.select.model.User;
import naver.shopping.select.repository.FolderRepository;
import naver.shopping.select.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final FolderRepository folderRepository;

    public static final int MIN_MY_PRICE = 100;

    public static ProductPagingResponseDto productPagingResponseDto = new ProductPagingResponseDto();


    @Autowired
    public ProductService(ProductRepository productRepository, FolderRepository folderRepository){
        this.productRepository = productRepository;
        this.folderRepository = folderRepository;
    }

    public Product createProduct(ProductRequestDto requestDto, Long userId){

        if(userId == null){
            throw new IllegalArgumentException("회원 Id 가 유효하지 않습니다");
        }

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Product product = new Product(requestDto,userId);

        productRepository.save(product);

        return product;
    }

    public Product updateProduct(Long id, ProductMypriceRequestDto requestDto){

        int myPrice = requestDto.getMyprice();
        if (myPrice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(()->new NullPointerException("해당 아이디가 존재하지 않습니다."));

        int myprice = requestDto.getMyprice();
        product.setMyprice(myprice);
        productRepository.save(product);

        return product;
    }

    public Page<ProductPagingResponseDto> getProducts(Long userId, int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Product> products = productRepository.findAllByUserId(userId, pageable);

        return productPagingResponseDto.changeProductToProductDto(products,pageable);
    }

    public Page<ProductPagingResponseDto> getAllProducts(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Product> products = productRepository.findAll(pageable);

        return productPagingResponseDto.changeProductToProductDto(products,pageable);

    }

    @Transactional
    public Product addFolder(Long productId, Long folderId, User user) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new NullPointerException("해당 아이디가 존재하지 않습니다."));

        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(()-> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        Long loginUserId = user.getId();

        if(!loginUserId.equals(product.getUserId()) && !folder.getId().equals(loginUserId)){
            throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 회원님의 폴더가 아닙니다.");
        }

        product.addFolder(folder);
        return product;
    }

}



