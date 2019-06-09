package com.froso.ufp.core.domain.documents.simple.status;

import com.froso.ufp.core.domain.interfaces.*;

/**
 * Created by ckleinhuis on 20.12.2016.
 */
public class MemoryStatus implements IDataObject {

    private Long freeMemoryBytes=0L;
    private Long totalMemoryBytes=0L;
    private Long maxMemoryBytes=0L;

    public static MemoryStatus getCurrentMemoryStatus() {

        MemoryStatus result = new MemoryStatus();

        result.setFreeMemoryBytes(Runtime.getRuntime().freeMemory());
        result.setTotalMemoryBytes(Runtime.getRuntime().totalMemory());
        result.setMaxMemoryBytes(Runtime.getRuntime().maxMemory());
        return result;

    }

    public Long getFreeMemoryBytes() {
        return freeMemoryBytes;
    }

    public void setFreeMemoryBytes(Long freeMemoryBytes) {
        this.freeMemoryBytes = freeMemoryBytes;
    }

    public Long getTotalMemoryBytes() {
        return totalMemoryBytes;
    }

    public void setTotalMemoryBytes(Long totalMemoryBytes) {
        this.totalMemoryBytes = totalMemoryBytes;
    }

    public Long getMaxMemoryBytes() {
        return maxMemoryBytes;
    }

    public void setMaxMemoryBytes(Long maxMemoryBytes) {
        this.maxMemoryBytes = maxMemoryBytes;
    }

    public Long getUsedMemoryBytes() {
        return totalMemoryBytes - freeMemoryBytes;
    }

    public Double getUsedMemoryMegaBytes() {
        return getUsedMemoryBytes() / 1024.0 / 1024.0;
    }

    public Double getFreeMemoryMegaBytes() {
        return getFreeMemoryBytes() / 1024.0 / 1024.0;
    }

    public Double getTotalMemoryMegaBytes() {
        return getTotalMemoryBytes() / 1024.0 / 1024.0;
    }


}
