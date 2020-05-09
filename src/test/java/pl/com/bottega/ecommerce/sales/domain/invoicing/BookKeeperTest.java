package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookKeeperTest {

    @Mock
    private TaxPolicy taxPolicyMock;

    @Mock
    private ClientData clientDataMock;

    private BookKeeper bookKeeper;
    private InvoiceRequest invoiceRequest;
    private RequestItem requestItem;

    @Before public void setUp() throws Exception {
        bookKeeper = new BookKeeper(new InvoiceFactory());
        invoiceRequest = new InvoiceRequest(clientDataMock);

        requestItem = new RequestItem(
                new ProductDataBuilder().withName("test").withMoney(Money.ZERO).withType(ProductType.STANDARD).build(),
                1,
                Money.ZERO
        );

        taxPolicyMock = mock(TaxPolicy.class);
        when(taxPolicyMock.calculateTax(ProductType.STANDARD, Money.ZERO)).thenReturn(new Tax(Money.ZERO, ""));
    }

    // state tests
    @Test public void invoiceRequestWithOneItemReturnInvoiceWithOnePosition() {
        invoiceRequest.add(requestItem);
        assertThat(bookKeeper.issuance(invoiceRequest, taxPolicyMock).getItems().size(), is(1));
    }

    @Test public void invoiceRequestWithNoItemReturnInvoiceWithNoPosition() {
        assertThat(bookKeeper.issuance(invoiceRequest, taxPolicyMock).getItems().size(), is(0));
    }

    @Test public void invoiceRequestWithFiveItemReturnInvoiceWithFivePosition() {
        int itemsQuantity = 5;

        for (int i = 0; i < itemsQuantity; i++) {
            invoiceRequest.add(requestItem);
        }

        assertThat(bookKeeper.issuance(invoiceRequest, taxPolicyMock).getItems().size(), is(itemsQuantity));
    }

    // behaviour tests
    @Test public void invoiceRequestWithTwoItemsInvokesCalcualteTaxTwoTimes() {
        invoiceRequest.add(requestItem);
        invoiceRequest.add(requestItem);

        bookKeeper.issuance(invoiceRequest, taxPolicyMock);

        assertThat(Mockito.mockingDetails(taxPolicyMock).getInvocations().size(), is(2));
    }

    @Test public void issuanceInvokeWithNullInvoiceRequestThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> bookKeeper.issuance(null, taxPolicyMock));
    }

    @Test public void issuanceInvokeWithNullTaxPolicyThrowsNullPointerException() {
        assertDoesNotThrow(() -> bookKeeper.issuance(invoiceRequest, null));
    }
}
