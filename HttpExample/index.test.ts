import httpTrigger from "./index";
const context = require("../__tests__/defaultContext");

test("Http trigger should return known text", async () => {
    const request = {
        query: { name: "Bill" }
    };
    await httpTrigger(context, request);

    expect(context.log.mock.calls.length).toBe(1);
    expect(context.res.body).toEqual("Hello, Bill. This HTTP triggered function executed successfully.");
});
