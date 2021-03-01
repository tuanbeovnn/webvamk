package com.vamkthesis.web.builder;

public class ProductSearchBuilder {

    private String productName;
    private String origin;
    private String warranty;
    private Double priceFrom;
    private Double priceTo;

    public String getProductName() {
        return productName;
    }

    public Double getPriceFrom() {
        return priceFrom;
    }

    public Double getPriceTo() {
        return priceTo;
    }

    public String getOrigin() {
        return origin;
    }

    public String getWarranty() {
        return warranty;
    }

    public ProductSearchBuilder(Builder builder) {
        this.productName = builder.productName;
        this.origin = builder.origin;
        this.warranty = builder.warranty;
        this.priceFrom = builder.priceFrom;
        this.priceTo = builder.priceTo;
    }

    public static class Builder {
        private String productName;
        private String origin;
        private String warranty;
        private Double priceFrom;
        private Double priceTo;

        public Builder setPriceFrom(Double priceFrom) {
            this.priceFrom = priceFrom;
            return this;
        }

        public Builder setPriceTo(Double priceTo) {
            this.priceTo = priceTo;
            return this;
        }

        public Builder() {
        }

        public Builder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder setOrigin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder setWarranty(String warranty) {
            this.warranty = warranty;
            return this;
        }
        public ProductSearchBuilder build() {
            return new ProductSearchBuilder(this);
        }
    }
}
