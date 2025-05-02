package com.quickpick.ureca.reserve.v2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReserveServiceImplV2 implements ReserveServiceV2 {



}
