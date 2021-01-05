package cn.com.glsx.supplychain.mapstruct;

import cn.com.glsx.supplychain.model.AttribInfo;
import cn.com.glsx.supplychain.vo.AttribInfoVo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
public class AttribInfoVoMapperImpl implements AttribInfoVoMapper {

    @Override
    public AttribInfoVo AttribInfoToAttribInfoVo(AttribInfo attribInfo) {
        if ( attribInfo == null ) {
            return null;
        }

        AttribInfoVo attribInfoVo = new AttribInfoVo();

        if ( attribInfo.getId() != null ) {
            attribInfoVo.setId( String.valueOf( attribInfo.getId() ) );
        }
        if ( attribInfo.getType() != null ) {
            attribInfoVo.setType( String.valueOf( attribInfo.getType() ) );
        }
        attribInfoVo.setName( attribInfo.getName() );
        attribInfoVo.setComment( attribInfo.getComment() );

        return attribInfoVo;
    }

    @Override
    public List<AttribInfoVo> AttribInfoToAttribInfoVos(List<AttribInfo> AttribInfoList) {
        if ( AttribInfoList == null ) {
            return null;
        }

        List<AttribInfoVo> list = new ArrayList<AttribInfoVo>( AttribInfoList.size() );
        for ( AttribInfo attribInfo : AttribInfoList ) {
            list.add( AttribInfoToAttribInfoVo( attribInfo ) );
        }

        return list;
    }
}
