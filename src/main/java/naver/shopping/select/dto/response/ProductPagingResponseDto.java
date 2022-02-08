package naver.shopping.select.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import naver.shopping.select.model.Folder;
import naver.shopping.select.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ProductPagingResponseDto {

    private Long id;
    private String image;
    private String link;
    private int lprice;
    private int myprice;
    private Long userId;
    private List<Folder> folderList = new ArrayList<>();

    public ProductPagingResponseDto(Product p){
        id = p.getId();
        image = p.getImage();
        link = p.getLink();
        lprice = p.getLprice();
        myprice = p.getMyprice();
        userId = p.getUserId();
        folderList = p.getFolderList();
    }

    public Page<ProductPagingResponseDto> changeProductToProductDto(Page<Product> products, Pageable pageable){
        int totalElements = (int)products.getTotalElements();
        return new PageImpl<ProductPagingResponseDto>(products.getContent()
                .stream()
                .map(p -> new ProductPagingResponseDto(p))
                .collect(Collectors.toList()), pageable, totalElements);
    }
}
