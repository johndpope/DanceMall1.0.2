package hzxmkuar.com.applibrary.domain.merchant;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/28.
 */
@Data
public class CaseInfoTo implements Serializable{
    private int imageId;
    private String imageUrl;
    private String caseDes;
    private int id;
}
