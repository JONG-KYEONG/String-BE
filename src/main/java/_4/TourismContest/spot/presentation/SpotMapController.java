package _4.TourismContest.spot.presentation;

import _4.TourismContest.oauth.application.CurrentUser;
import _4.TourismContest.oauth.application.UserPrincipal;
import _4.TourismContest.spot.application.SpotService;
import _4.TourismContest.spot.dto.event.SpotDetailInfoDto;
import _4.TourismContest.spot.dto.event.SpotMapResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/spot/map")
@RequiredArgsConstructor
public class SpotMapController {

    private final SpotService spotService;

    @GetMapping("/{stadium}/{category}/{level}/{nowX}/{nowY}")
    @Operation(summary = "구장 별 주변관광지에 대한 마커를 찍기 위한 정보 ", description = "구장, 카테고리 필터를 사용하여, 구장 근처의 각 카테고리에 맞는 주변 볼거리 정보를 제공합니다. \n" +
            "radius: (km 단위)")
    public ResponseEntity<List<SpotMapResponseDto>> getStadiumNearSpot(@PathVariable String stadium, @PathVariable String category, @PathVariable int level,
                                                                       @PathVariable double nowX, @PathVariable double nowY, @CurrentUser UserPrincipal userPrincipal) throws IOException {
        List<SpotMapResponseDto> nearSpot = spotService.getNearSpot(nowX, nowY, stadium, category, level, userPrincipal);
        return ResponseEntity.ok(nearSpot);
    }

    @GetMapping("/{stadium}/{contentId}")
    @Operation(summary = "주변 관광지 마커 클릭", description = "주변 관광지 마커를 클릭 시 상세 내용을 출력합니다.")
    public ResponseEntity<SpotDetailInfoDto> getNearSpotDetailInfo(@PathVariable String stadium , @PathVariable Long contentId, @CurrentUser UserPrincipal userPrincipal){
        SpotDetailInfoDto nearSpotDetailInfo = spotService.getNearSpotDetailInfo(stadium, contentId, userPrincipal);
        return ResponseEntity.ok(nearSpotDetailInfo);
    }
}