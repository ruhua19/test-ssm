package com.ruhua.springtest.param;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName user_code_collection_relation
 */

@Data
public class UserCodeCollectionRelationParam implements Serializable {
    /**
     * 
     */

    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer codeId;

    /**
     * 
     */
    private Date createAt;


    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserCodeCollectionRelationParam other = (UserCodeCollectionRelationParam) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCodeId() == null ? other.getCodeId() == null : this.getCodeId().equals(other.getCodeId()))
            && (this.getCreateAt() == null ? other.getCreateAt() == null : this.getCreateAt().equals(other.getCreateAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCodeId() == null) ? 0 : getCodeId().hashCode());
        result = prime * result + ((getCreateAt() == null) ? 0 : getCreateAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", codeId=").append(codeId);
        sb.append(", createAt=").append(createAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}