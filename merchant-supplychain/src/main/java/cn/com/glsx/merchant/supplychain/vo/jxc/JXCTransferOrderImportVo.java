package cn.com.glsx.merchant.supplychain.vo.jxc;

import cn.com.glsx.supplychain.jxc.dto.JXCTransferOrderImportDTO;

import java.util.List;

/**
 * @author luoqiang
 * @version 1.0
 * @program: supplychain
 * @description:
 * @date 2020/10/27 11:47
 */
public class JXCTransferOrderImportVo {
  private   List<JXCTransferOrderImportDTO> transferOrderImportDTOList;

  public List<JXCTransferOrderImportDTO> getTransferOrderImportDTOList() {
    return transferOrderImportDTOList;
  }

  public void setTransferOrderImportDTOList(List<JXCTransferOrderImportDTO> transferOrderImportDTOList) {
    this.transferOrderImportDTOList = transferOrderImportDTOList;
  }

  @Override
  public String toString() {
    return "JXCTransferOrderImportVo{" +
            "transferOrderImportDTOList=" + transferOrderImportDTOList +
            '}';
  }
}
