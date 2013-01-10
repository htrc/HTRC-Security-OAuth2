package org.wso2.carbon.identity.entitlement.policy.search;

import org.wso2.carbon.identity.entitlement.dto.EntitledResultSetDTO;

/**
 * Encapsulate result with time stamp 
 */
public class SearchResult {

    /**
     * Result
     */
    private EntitledResultSetDTO resultSetDTO;

    /**
     * time stamp
     */
    private long cachedTime;

    public EntitledResultSetDTO getResultSetDTO() {
        return resultSetDTO;
    }

    public void setResultSetDTO(EntitledResultSetDTO resultSetDTO) {
        this.resultSetDTO = resultSetDTO;
    }

    public long getCachedTime() {
        return cachedTime;
    }

    public void setCachedTime(long cachedTime) {
        this.cachedTime = cachedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchResult)) return false;

        SearchResult that = (SearchResult) o;

        if (cachedTime != that.cachedTime) return false;
        if (resultSetDTO != null ? !resultSetDTO.equals(that.resultSetDTO) : that.resultSetDTO != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resultSetDTO != null ? resultSetDTO.hashCode() : 0;
        result = 31 * result + (int) (cachedTime ^ (cachedTime >>> 32));
        return result;
    }
}
