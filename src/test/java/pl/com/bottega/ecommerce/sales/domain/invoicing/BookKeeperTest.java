package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookKeeperTest {

    @Mock
    private TaxPolicy taxPolicyMock;

    private ClientData clientDataMock;

    private BookKeeper bookKeeper;
    private RequestItem requestItem;

    @Before public void setUp() throws Exception {
        bookKeeper = new BookKeeper(new InvoiceFactory());

        ProductData productData = new ProductDataBuilder()
                .withName("test")
                .withMoney(Money.ZERO)
                .withType(ProductType.STANDARD)
                .build();

        requestItem = new RequestItemBuilder()
                .withMoney(Money.ZERO)
                .withQuantity(1)
                .withProductData(productData)
                .build();

        when(taxPolicyMock.calculateTax(ProductType.STANDARD, Money.ZERO)).thenReturn(new Tax(Money.ZERO, ""));
    }

    // state tests
    @Test public void invoiceRequestWithOneItemReturnInvoiceWithOnePosition() {
        InvoiceRequest invoiceRequest = new InvoiceRequestBuilder()
                .withClientData(clientDataMock)
                .withRequestItem(requestItem)
                .withItemQuantity(1)
                .build();

        assertThat(bookKeeper.issuance(invoiceRequest, taxPolicyMock).getItems().size(), is(1));
    }

    @Test public void invoiceRequestWithNoItemReturnInvoiceWithNoPosition() {
        InvoiceRequest invoiceRequest = new InvoiceRequestBuilder()
                .withClientData(clientDataMock)
                .build();

        assertThat(bookKeeper.issuance(invoiceRequest, taxPolicyMock).getItems().size(), is(0));
    }

    @Test public void invoiceRequestWithFiveItemReturnInvoiceWithFivePosition() {
        int itemsQuantity = 5;

        InvoiceRequest invoiceRequest = new InvoiceRequestBuilder()
                .withClientData(clientDataMock)
                .withRequestItem(requestItem)
                .withItemQuantity(itemsQuantity)
                .build();

        assertThat(bookKeeper.issuance(invoiceRequest, taxPolicyMock).getItems().size(), is(itemsQuantity));
    }

    // behaviour tests
    @Test public void invoiceRequestWithTwoItemsInvokesCalcualteTaxTwoTimes() {
        InvoiceRequest invoiceRequest = new InvoiceRequestBuilder()
                .withClientData(clientDataMock)
                .withRequestItem(requestItem)
                .withItemQuantity(2)
                .build();

        bookKeeper.issuance(invoiceRequest, taxPolicyMock);
        assertThat(Mockito.mockingDetails(taxPolicyMock).getInvocations().size(), is(2));
    }

    @Test public void issuanceInvokeWithNullInvoiceRequestThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookKeeper.issuance(null, taxPolicyMock));
    }

    @Test public void issuanceInvokeWithNullTaxPolicyThrowsNullPointerException() {
        InvoiceRequest invoiceRequest = new InvoiceRequestBuilder()
                .withClientData(clientDataMock)
                .build();

        assertDoesNotThrow(() -> bookKeeper.issuance(invoiceRequest, null));
    }
}
