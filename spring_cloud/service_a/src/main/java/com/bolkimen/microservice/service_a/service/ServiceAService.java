package com.bolkimen.microservice.service_a.service;

import com.bolkimen.microservice.service_a.client.ServiceBServiceClient;
import com.bolkimen.microservice.service_a.dto.ABRecord;
import com.bolkimen.microservice.service_a.dto.ARecord;
import com.bolkimen.microservice.service_a.dto.BRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ServiceAService {
    @Autowired
    ServiceBServiceClient serviceBServiceClient;

    public Flux<ARecord> listAllARecords() {
        return Flux.just("AHello", "Aworld", "AError")
                .map(str -> new ARecord(str));
    }

    public Flux<ABRecord> listAllAandBRecords() {
        Flux<ARecord> aRecords = this.listAllARecords();
        Flux<BRecord> bRecords = serviceBServiceClient.getBRecord();
        return aRecords.zipWith(bRecords)
                .map(t -> new ABRecord(((ARecord)t.get(0)).aName(), ((BRecord)t.get(1)).bName()));
    }
}
