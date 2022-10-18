package com.oss.carbonadministrator.service.image;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.electricity.ElectricityInfo;
import com.oss.carbonadministrator.domain.user.User;
import com.oss.carbonadministrator.dto.request.image.ImgDataRequest;
import com.oss.carbonadministrator.exception.DataInfoNotFoundException;
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

        User targetUser = user.get();
        targetUser.setCount(targetUser.getCount() + 1);
        userRepository.saveAndFlush(targetUser);

        Optional<Bill> targetBill = billRepository.findBillByEmailAndYearAndMonth(user.get().getEmail(), year, month);

        if (targetBill.isEmpty()){
            Bill bill = Bill.builder()
                    .user(user.get())
                    .electricityInfoList(recognizedElecData)
                    .year(year)
                    .month(month)
                    .build();

            return billRepository.saveAndFlush(bill);
        }

        Bill bill = targetBill.get();
        electricityRepository.delete(bill.getElectricityInfoList());
        bill.setElectricityInfoList(recognizedElecData);

        return billRepository.saveAndFlush(bill);
    }

    @Transactional
    public void update(Long id, ImgDataRequest updateData) {

        ElectricityInfo savedData = electricityRepository.findById(id)
            .orElseThrow(() -> new DataInfoNotFoundException("수정할 전기 데이터가 없습니다."));

        savedData.update(updateData);
    }

}
