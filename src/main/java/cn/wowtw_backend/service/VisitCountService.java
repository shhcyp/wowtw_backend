package cn.wowtw_backend.service;

import cn.wowtw_backend.model.user.VisitCount;
import cn.wowtw_backend.repository.VisitCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class VisitCountService {

    private final VisitCountRepository visitCountRepository;

    @Autowired
    public VisitCountService(VisitCountRepository visitCountRepository) {
        this.visitCountRepository = visitCountRepository;
    }

    @Transactional
    public void incrementVisitCount() {
        VisitCount visitCount = visitCountRepository.findById(1L).orElseThrow(() -> new RuntimeException("VisitCount not found"));
        visitCount.setCount(visitCount.getCount() + 1);
        visitCountRepository.save(visitCount);
    }

    public int getVisitCount() {
        VisitCount visitCount = visitCountRepository.findById(1L).orElseThrow(() -> new RuntimeException("VisitCount not found"));
        return visitCount.getCount();
    }
}
