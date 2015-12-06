package io.gof.tender.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.gof.tender.util.CustomShortDateDeserializer;
import io.gof.tender.util.CustomShortDateSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Builder
@Document(collection = "projects")
public class Project extends BaseEntity {
    @Id
    private String id;

    private String code;                //kd_paket
    private String name;                //nama_pake
    private String organizer;           //nama_panitia
    private Date announcementDate;      //tgl_pengumuman
    private Double contractValue;       //nilai_kontrak
    private Date contractDate;          //tanggal_kontrak
    private String biddingCode;         //kd_lelang
    private Date biddingEndDate;        //tanggal_akhir_lelang
    private String fundSource;          //sumber_dana
    private String fiscalYear;          //tahun_anggaran
    private String category;            //kategori
    private String districtCode;        //kd_kabupaten
    private String districtName;        //nama_kabupaten
    private String provinceName;        //nama_propinsi

    @JsonSerialize(using = CustomShortDateSerializer.class)
    @JsonDeserialize(using = CustomShortDateDeserializer.class)
    private Date start;

    @JsonSerialize(using = CustomShortDateSerializer.class)
    @JsonDeserialize(using = CustomShortDateDeserializer.class)
    private Date due;

    @DBRef
    private Milestone[] milestones;

    @DBRef
    private Location location;

    @DBRef
    private Vendor vendor;
    private Tuple workUnit;
    private Tuple agency;
    private Tuple purchaser;
    private Price price;

    @DBRef
    private Comment[] comments;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection = "milestones")
    public static class Milestone {
        @Id
        private String id;

        @JsonSerialize(using = CustomShortDateSerializer.class)
        @JsonDeserialize(using = CustomShortDateDeserializer.class)
        private Date due;

        private String title;
        private String content;

        private Album album;

        private String[] highlights;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Album {

        private Image[] images;

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Image {
            private String title;
            private String thumbSource;
            private String thumbBase64;
            private String source;
            private String base64;
            private String alt;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Price {

        private Double estimated;
        private Double ceiling;
        private Double winning;
    }
}
