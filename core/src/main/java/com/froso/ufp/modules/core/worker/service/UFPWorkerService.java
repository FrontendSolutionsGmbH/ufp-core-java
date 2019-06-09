package com.froso.ufp.modules.core.worker.service;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.worker.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class UFPWorkerService extends AbstractClientRefService<UFPWorker> {
    @Autowired
    private WorkerManagerService workerManagerService;


    public UFPWorker getWorkerData(String name) {
        return findOneByKeyValue("name", name);
    }

    @Override
    public void delete(UFPWorker element) {
        throw new UFPWorkerException.UFPWorkerCannotBeDeleted();
    }

    @Override
    protected void prepareResultElement(UFPWorker element) {
        // here we set for any result element if the instance for that worker is actually present
        element.setInstancePresent(workerManagerService.isInstancePresent(element.getName()));
        // virtual template function to modify result
    }

    @Override
    public UFPWorker createNewDefault() {
        throw new UFPWorkerException.UFPWorkerCannotBeCreated();
    }

}
