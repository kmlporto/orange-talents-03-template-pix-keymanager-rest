package br.com.zup.edu.comum.grpc

import com.google.protobuf.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset


fun Timestamp.toLocalDate(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(this.seconds, this.nanos, ZoneOffset.UTC)
}