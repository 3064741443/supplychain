package glsx.com.cn.task.model;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Settlement
 * @Author admin
 * @Param
 * @Date 2019/7/1 15:28
 * @Version
 **/

public class Material implements Serializable {

    private Integer id;

    /**
     * 物料编号
     */
    private String materialCode;

    /**
     * 物料价格
     */
    private double materialPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public double getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(double materialPrice) {
        this.materialPrice = materialPrice;
    }
}
