package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class RequestItemBuilder {

    private Money money;
    private ProductData productData;
    private int quantity;

    public RequestItemBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public RequestItemBuilder withMoney(Money money) {
        this.money = money;
        return this;
    }

    public RequestItemBuilder withProductData(ProductData productData) {
        this.productData = productData;
        return this;
    }

    public RequestItem build() {
        return new RequestItem(productData, quantity, money);
    }
}
