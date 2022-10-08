package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.dto.request.image.ElecImgRequest;
import com.oss.carbonadministrator.exception.ElecInfoNotFoundException;
import com.oss.carbonadministrator.exception.user.HasNoUserException;
import com.oss.carbonadministrator.repository.bill.BillRepository;
import com.oss.carbonadministrator.repository.electricity.ElectricityRepository;
import com.oss.carbonadministrator.repository.user.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElectricityImageService {

    private final UserRepository userRepository;

    private final ElectricityRepository electricityRepository;
    private final BillRepository billRepository;

    @Transactional
    public Bill save(String email, int year, int month, ElectricityInfo recognizedElecData) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new HasNoUserException("해당하는 유저가 존재하지 않습니다.");
        }

        Bill bill = Bill.builder()
            .user(user.get())
            .electricityInfoList(recognizedElecData)
            .year(year)
            .month(month)
            .build();

        return billRepository.save(bill);
    }

    @Transactional
    public void update(Long id, ElecImgRequest updateData) {

        ElectricityInfo savedData = electricityRepository.findById(id)
            .orElseThrow(() -> new ElecInfoNotFoundException("수정할 전기 데이터가 없습니다."));

        savedData.update(updateData);
    }

}
