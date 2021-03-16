import httpTrigger from "../../HttpExample/index";

let context = null;

beforeEach(() => {
    context = {
        log: jest.fn()
    }
});

test("when request has name query string, return greeting message", async () => {
    const request = {
        query: { name: "Bill" }
    };
    await httpTrigger(context, request);

    expect(context.log.mock.calls.length).toBe(1);
    expect(context.res.body).toEqual("Hello, Bill. This HTTP triggered function executed successfully.");
});

test("when request has not name query string, return greeting message", async () => {
    const request = { query: {} };
    await httpTrigger(context, request);

    expect(context.log.mock.calls.length).toBe(1);
    expect(context.res.body).toEqual("This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response.");
});
