package kr.co.artinus.infra.external.dto;

public record GetRandomResponse(
        String status,
        int min,
        int max,
        int random
) {}
