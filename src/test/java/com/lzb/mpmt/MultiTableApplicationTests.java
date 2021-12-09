package com.lzb.mpmt;

import com.lzb.mpmt.service.multiwrapper.constant.MultiConstant;
import com.lzb.mpmt.service.multiwrapper.dto.IMultiPage;
import com.lzb.mpmt.service.multiwrapper.dto.MultiAggregateResult;
import com.lzb.mpmt.service.multiwrapper.dto.MultiPage;
import com.lzb.mpmt.service.multiwrapper.executor.MultiExecutor;
import com.lzb.mpmt.service.multiwrapper.util.json.jackson.JsonUtil;
import com.lzb.mpmt.service.multiwrapper.wrapper.MultiWrapper;
import com.lzb.mpmt.service.multiwrapper.wrapper.MultiWrapperMain;
import com.lzb.mpmt.service.multiwrapper.wrapper.MultiWrapperSub;
import com.lzb.mpmt.test.model.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest(classes = MultiTableApplication.class)
class MultiTableApplicationTests {

    /**
     * 简单查询test
     */
    @Test
    @SneakyThrows
    void testQuerySimple() {
        List<UserStaff> userStaffsSimple = MultiExecutor.list(new MultiWrapper<>(MultiWrapperMain.lambda(UserStaff.class), User.class, UserAddress.class, Address.class));
        System.out.println("testQuerySimple=" + JsonUtil.toString(userStaffsSimple));
    }

    /**
     * 简单查询test
     */
    @Test
    @SneakyThrows
    void testQueryParamMap() {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("userStaff_sex", "#lr#0");
        List<UserStaff> userStaffsSimple = MultiExecutor.list(new MultiWrapper<>(MultiWrapperMain.lambda(UserStaff.class), User.class, UserAddress.class, Address.class).extendParams(paramMap));
        System.out.println("testQueryParamMap=" + JsonUtil.toString(userStaffsSimple));
    }

    /**
     * 分页查询
     */
    @Test
    @SneakyThrows
    void testQueryPage() {
        IMultiPage<UserStaff> page = MultiExecutor.page(new MultiPage<>(1, 10),
                new MultiWrapper<>(MultiWrapperMain.lambda(UserStaff.class)
                        .desc(BaseModel::getCreateTime)
                        , User.class, UserAddress.class, Address.class));
        System.out.println("testQueryPage=" + JsonUtil.toString(page));
    }

    /**
     * 基本聚合查询
     */
    @Test
    @SneakyThrows
    void testQueryAggregate() {
        MultiAggregateResult aggregateSumAll = MultiExecutor.aggregate(new MultiWrapper<>(MultiWrapperMain.lambda(UserStaff.class).aggregateAll(MultiConstant.MultiAggregateTypeEnum.SUM),
                MultiWrapperSub.lambda(User.class)
        ));
        System.out.println("aggregateSumAll=" + aggregateSumAll.getSum());

        MultiAggregateResult aggregateSpecific = MultiExecutor.aggregate(new MultiWrapper<>(MultiWrapperMain.lambda(UserStaff.class),
                MultiWrapperSub.lambda(User.class).sum(User::getBalance, "tempBalance")
        ));
        System.out.println("aggregateSpecific=" + aggregateSpecific.getSum().get("tempBalance"));
    }

    /**
     * 复杂查询test
     */
    @Test
    void testQueryComplex() {
        //1.复杂查询
        List<UserStaff> userStaffsComplex = MultiExecutor.list(MultiWrapper
                .main(
                        MultiWrapperMain.lambda(UserStaff.class)
                                .select(UserStaff::getSex, UserStaff::getStaffName)
                                .and(w ->
                                        w.eq(true, UserStaff::getStaffName, "StaffName3")
                                                .or()
                                                .and(w2 -> w2.eq(true, UserStaff::getStaffName, "StaffName4")
                                                        .eq(true, UserStaff::getStaffName, "StaffName4"))

                                )
                                .eq(true, UserStaff::getSex, 1)
                                .likeAll(true, UserStaff::getStaffName, "111")
                                .notIn(true, UserStaff::getStaffName, "111", "11122", "1112")
                                .limit(0, 20, Collections.singletonList("create_time desc"))

                )
                .leftJoin(MultiWrapperSub.lambda(User.class)
                        .select(User::getUsername)
                        .gt(BaseModel::getCreateTime, new Date())
                        .gt(BaseModel::getUpdateTime, LocalDateTime.now())
                        .in(BaseModel::getId, "1", "1", "3")
                        .likeAll(User::getUsername, "1")
                        .mainWhere(mainWhere ->
                                mainWhere.eq(User::getSex, 1)
                        )
                )
                .leftJoin(MultiWrapperSub.lambda(UserAddress.class)
                        .select(UserAddress::getProvince)
                        .gt(UserAddress::getId, "1")
                ));
        System.out.println("testQueryComplex=" + JsonUtil.toString(userStaffsComplex));
    }
}
