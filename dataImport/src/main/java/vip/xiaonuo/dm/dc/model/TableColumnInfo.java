package vip.xiaonuo.dm.dc.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class TableColumnInfo {
    private String TABLE_NAME;
    private String COLUMN_NAME;
    private String DATA_TYPE;
    private int DATA_LENGTH;
    private int DATA_PRECISION;
}
