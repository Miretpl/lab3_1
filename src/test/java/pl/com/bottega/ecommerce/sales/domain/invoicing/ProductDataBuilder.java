package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductDataBuilder {

    private String name = "";
    private Money money;
    private ProductType productType;

    public ProductDataBuilder withMoney(Money money) {
        this.money = money;
        return this;
    }

    public ProductDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductDataBuilder withType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public ProductData build() {
        return new Product(Id.generate(), money, name, productType).generateSnapshot();
    }
}
