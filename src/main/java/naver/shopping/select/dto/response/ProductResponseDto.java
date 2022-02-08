package naver.shopping.select.dto.response;

import lombok.Data;
import naver.shopping.select.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

@Data
public class ProductResponseDto {

    private Long id;
    private String image;
    private String link;
    private int lprice;
    private int myprice;
    private Long userId;

    public ProductResponseDto(Product p){
        id = p.getId();
        image = p.getImage();
        link = p.getLink();
        lprice = p.getLprice();
        myprice = p.getMyprice();
        userId = p.getUserId();
    }

}
