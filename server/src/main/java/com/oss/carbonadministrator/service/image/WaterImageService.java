package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.domain.water.WaterInfo;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import com.oss.carbonadministrator.exception.DataInfoNotFoundException;
import com.oss.carbonadministrator.exception.user.HasNoUserException;
import com.oss.carbonadministrator.repository.WaterRepository;
import com.oss.carbonadministrator.repository.bill.BillRepository;
import com.oss.carbonadministrator.repository.user.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WaterImageService {
    private final UserRepository userRepository;
    private final WaterRepository waterRepository;
    private final BillRepository billRepository;

    @Transactional
    public Bill save(String email, int year, int month, WaterInfo recognizedWaterData) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new HasNoUserException("해당하는 유저가 존재하지 않습니다.");
        }

        Bill bill = Bill.builder()
            .user(user.get())
            .waterInfoList(recognizedWaterData)
            .year(year)
            .month(month)
            .build();

        return billRepository.save(bill);
    }

    @Transactional
    public void update(Long id, ImgDataRequest updateData) {

        WaterInfo savedData = waterRepository.findById(id)
            .orElseThrow(() -> new DataInfoNotFoundException("수정할 수도 데이터가 없습니다."));

        savedData.update(updateData);
    }
}
