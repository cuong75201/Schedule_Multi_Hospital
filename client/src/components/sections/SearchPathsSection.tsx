import type { SearchRoute } from "./HeroSection";

const paths: { route: SearchRoute; title: string; description: string }[] = [
  { route: "hospital", title: "Bệnh viện", description: "Xem dịch vụ và lịch tiếp nhận tại nơi bạn muốn khám." },
  { route: "specialty", title: "Chuyên khoa", description: "Tìm nơi tiếp nhận chuyên khoa phù hợp với nhu cầu khám." },
  { route: "service", title: "Dịch vụ", description: "Tìm bệnh viện cung cấp dịch vụ bạn cần." },
];

const SearchPathsSection = ({ onSelectRoute }: { onSelectRoute: (route: SearchRoute) => void }) => (
  <section className="bg-white py-23 max-[760px]:py-18.75" id="cach-dat-lich" aria-labelledby="paths-title">
    <div className="mx-auto w-[calc(100%-80px)] max-w-330 max-[760px]:w-[min(calc(100%-36px),560px)] max-[420px]:w-[calc(100%-32px)]">
      <div className="mb-9.75 grid grid-cols-[.85fr_1fr] items-end gap-[10%] [&_p]:m-0 [&_p]:max-w-132.5 [&_p]:text-muted max-[760px]:grid-cols-1 max-[760px]:gap-6.5">
        <h2 className="mb-6 max-w-142.5 text-[clamp(31px,3.25vw,48px)] leading-[1.22] font-extrabold tracking-[-.045em] text-blue-deep" id="paths-title">Tìm theo cách của bạn</h2>
         <p>Biết nơi muốn khám hoặc chỉ mới biết nhu cầu của mình? CarePath cho phép bắt đầu từ thông tin bạn có.</p>
      </div>
       <div className="grid grid-cols-3 border-t-2 border-l border-t-blue-deep border-l-line max-[760px]:grid-cols-1">
        {paths.map(({ route, title, description }) => (
          <a
            key={route}
            className="flex min-h-53.5 flex-col items-start border-r border-b border-line px-8 pt-6.75 pb-6.5 no-underline transition-colors duration-200 hover:bg-blue-light focus-visible:bg-blue-light max-[760px]:min-h-0 max-[760px]:p-6"
            href="#tim-kiem"
            onClick={() => onSelectRoute(route)}
          >
            <span className="flex w-full items-center justify-between gap-3.5 text-[clamp(19px,2vw,26px)] leading-[1.3] font-extrabold tracking-[-.03em] text-blue-deep [&_svg]:size-5 [&_svg]:shrink-0 [&_svg]:text-blue">
              {title}
              <svg viewBox="0 0 20 20" fill="none" aria-hidden="true"><path d="M3 10h14m-6-6 6 6-6 6" stroke="currentColor" strokeWidth="1.8" /></svg>
            </span>
            <span className="mt-3.5 max-w-110 text-sm text-muted">{description}</span>
            <span className="mt-auto border-b border-current pt-3.75 text-[13px] font-extrabold text-blue max-[760px]:pt-4.5">Tìm theo {title.toLocaleLowerCase("vi")}</span>
          </a>
        ))}
      </div>
    </div>
  </section>
);

export default SearchPathsSection;
