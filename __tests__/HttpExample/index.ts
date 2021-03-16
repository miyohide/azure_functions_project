import httpTrigger from "../../HttpExample/index";

let context = null;

beforeEach(() => {
    // context.log("...")とcontext.log.info("...")のテストをしたい場合の参考
    // see. https://stackoverflow.com/questions/61732004/mocking-different-log-levels-on-azure-function-context-with-jest-in-javascript
    context = {
        log: (function () {
            let main = <any>jest.fn((m) => m);
            let info = jest.fn((m) => m);
            main.info = info;
            return main;
        })()
    }
});

test("when request has name query string, return greeting message", async () => {
    const request = {
        query: { name: "Bill" }
    };
    await httpTrigger(context, request);

    expect(context.log.mock.calls.length).toBe(1);
    expect(context.log.mock.calls[0][0]).toEqual("HTTP trigger function processed a request.");
    expect(context.log.info.mock.calls.length).toBe(1);
    expect(context.log.info.mock.calls[0][0]).toEqual("info log")
    expect(context.res.body).toEqual("Hello, Bill. This HTTP triggered function executed successfully.");
});

test("when request has not name query string, return greeting message", async () => {
    const request = { query: {} };
    await httpTrigger(context, request);

    expect(context.log.mock.calls.length).toBe(1);
    expect(context.log.mock.calls[0][0]).toEqual("HTTP trigger function processed a request.");
    expect(context.log.info.mock.calls.length).toBe(1);
    expect(context.log.info.mock.calls[0][0]).toEqual("info log")
    expect(context.res.body).toEqual("This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response.");
});
