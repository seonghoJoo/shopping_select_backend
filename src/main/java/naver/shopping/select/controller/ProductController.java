package naver.shopping.select.controller;

import naver.shopping.select.dto.request.ProductMypriceRequestDto;
import naver.shopping.select.dto.request.ProductRequestDto;
import naver.shopping.select.dto.response.ProductPagingResponseDto;
import naver.shopping.select.dto.response.ProductResponseDto;
import naver.shopping.select.model.Product;
import naver.shopping.select.model.UserRoleEnum;
import naver.shopping.select.security.UserDetailsImpl;
import naver.shopping.select.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.stream.Collectors;

@RestController //  JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    // 신규 상품 등록
    @PostMapping("/api/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails
                                 ) throws SQLException {

        // 로그인 되어 있는 회원 테이블의 ID
        Long userId = userDetails.getUser().getId();

        // Dto로 변환하기
        ProductResponseDto result = new ProductResponseDto(productService.createProduct(requestDto,userId));

        // 응답 보내기
        return result;
    }

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
        Product product = productService.updateProduct(id, requestDto);

        // 응답 보내기 (업데이트된 상품 id)
        return product.getId();
    }

    // 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public Page<ProductPagingResponseDto> getProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        // 로그인 되어 있는 회원 테이블의 ID
        Long userId = userDetails.getUser().getId();
        page -= 1;
        Page <ProductPagingResponseDto> products = productService.getProducts(userId, page, size, sortBy, isAsc);

        // 응답 보내기
        return products;
    }

    // 관리자용 등록된 모든 상품 목록 조회
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @GetMapping("/api/admin/products")
    public Page<ProductPagingResponseDto> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) throws SQLException {
        page -=1;
        Page<ProductPagingResponseDto> products = productService.getAllProducts(page,size,sortBy,isAsc);
        // 응답 보내기
        return products;
    }

    // 폴더 추가
    @PostMapping("/api/products/{productId}/folder")
    public Long addFolder(
            @PathVariable Long productId,
            @RequestParam Long folderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        Product product = productService.addFolder(productId, folderId, userDetails.getUser());
        return product.getId();
    }

}