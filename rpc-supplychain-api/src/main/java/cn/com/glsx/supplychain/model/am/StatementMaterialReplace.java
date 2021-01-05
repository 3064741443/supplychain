package cn.com.glsx.supplychain.model.am;

import java.io.Serializable;

import javax.persistence.Table;

@Table(name = "am_statement_material_replace")
public class StatementMaterialReplace implements Serializable{
    private Integer id;

    private String materialCodeOrg;

    private String materialNameOrg;

    private String materialCodeDst;

    private String materialNameDst;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterialCodeOrg() {
        return materialCodeOrg;
    }

    public void setMaterialCodeOrg(String materialCodeOrg) {
        this.materialCodeOrg = materialCodeOrg == null ? null : materialCodeOrg.trim();
    }

    public String getMaterialNameOrg() {
        return materialNameOrg;
    }

    public void setMaterialNameOrg(String materialNameOrg) {
        this.materialNameOrg = materialNameOrg == null ? null : materialNameOrg.trim();
    }

    public String getMaterialCodeDst() {
        return materialCodeDst;
    }

    public void setMaterialCodeDst(String materialCodeDst) {
        this.materialCodeDst = materialCodeDst == null ? null : materialCodeDst.trim();
    }

    public String getMaterialNameDst() {
        return materialNameDst;
    }

    public void setMaterialNameDst(String materialNameDst) {
        this.materialNameDst = materialNameDst == null ? null : materialNameDst.trim();
    }
}