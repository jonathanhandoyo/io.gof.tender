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

    private String code;
    private String name;


    @JsonSerialize(using = CustomShortDateSerializer.class)
    @JsonDeserialize(using = CustomShortDateDeserializer.class)
    private Date start;

    @JsonSerialize(using = CustomShortDateSerializer.class)
    @JsonDeserialize(using = CustomShortDateDeserializer.class)
    private Date due;

    @JsonSerialize(using = CustomShortDateSerializer.class)
    @JsonDeserialize(using = CustomShortDateDeserializer.class)
    private Date bidCreatedDate; //Tanggal Pembuatan

    private String workScope; //Lingkup pekerjaan
    private String description; //Keterangan
    private BiddingStep[] biddingSteps; //Tahap Lelang
    private String biddingStepsLink;
    private String organizer; //Instansi
    private String workUnit; //Satuan Kerja
    private String category; //Kategori
    private Method method;
    private String fiscalYear; //Tahun Anggaran
    private Price price;

    @DBRef
    private Location[] locations; //Lokasi Pekerjaan

    private Qualification qualification;
    private Bidder[] bidders; //Peserta Lelang

    @DBRef
    private Vendor winner;

    @Getter
    @Setter
    @Builder
    public static class BiddingStep extends BaseEntity {
        private String name;

        @JsonSerialize(using = CustomShortDateSerializer.class)
        @JsonDeserialize(using = CustomShortDateDeserializer.class)
        private Date start;

        @JsonSerialize(using = CustomShortDateSerializer.class)
        @JsonDeserialize(using = CustomShortDateDeserializer.class)
        private Date end;

        private String revision;
    }

    @Getter
    @Setter
    @Builder
    public static class ContractType extends BaseEntity {
        private String paymentMethod; // Cara Pembayaran
        private String fiscalYearImposition; //Pembebanan Tahun Anggaran
        private String sourceOfFund; //Sumber Pendanaan
    }

    @Getter
    @Setter
    @Builder
    public static class Method extends BaseEntity {
        private String biddingMethod; //Metode Pengadaan
        private String qualificationMethod; // Metode Kualifikasi//
        private String evaluationMethod; // Metode Evaluasi
        private String documentMethod; //Metode Dokumen
    }

    @Getter
    @Setter
    @Builder
    public static class Qualification extends BaseEntity {
        private Tuple[] permits;
        private Tuple[] requirements;
        String link;

    }

    @Getter
    @Setter
    @Builder
    public static class Bidder extends BaseEntity {
        private String taxId; //NPWP
        private String name;
        private Double askingPrice;
        private Double correctedPrice;
        private Evaluation evaluation;
        private Boolean winner;

        @Getter
        @Setter
        @Builder
        public static class Evaluation extends BaseEntity {
            private Boolean administration = false;
            private Boolean technical = false;
            private Boolean price = false;
            private Boolean qualification = false;
            private Boolean qualificationProof = false;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Price extends BaseEntity {
        private Double estimated; //Nilai HPS
        private Double ceiling; //Nilai Page
        private Double negotiated;
    }
}
