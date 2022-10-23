/**
 *  Copyright 2022 Carbon_Developers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.oss.carbonadministrator.repository.bill;

import com.oss.carbonadministrator.domain.bill.Bill;
import com.oss.carbonadministrator.domain.user.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query(value = "select b from Bill b join b.user u where b.year = ?2 and b.month = ?3 and u.email = ?1")
    Optional<Bill> findBillByEmailAndYearAndMonth(String email, int year, int month);

    List<Bill> findAllByUser(User user);
}
