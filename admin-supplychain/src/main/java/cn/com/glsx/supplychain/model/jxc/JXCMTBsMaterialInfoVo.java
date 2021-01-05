package cn.com.glsx.supplychain.model.jxc;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/8/10 15:40
 */
public class JXCMTBsMaterialInfoVo implements Serializable {
    @ApiModelProperty(name = "materialCode", notes = "物料编码", dataType = "string", required = false, example = "")
    private String materialCode;
    @ApiModelProperty(name = "materialName", notes = "物料名称", dataType = "string", required = false, example = "")
    private String materialName;

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
}
